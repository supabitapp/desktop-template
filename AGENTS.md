# Agent guide

A Rust desktop app on [Rune](https://github.com/anomalyco/rune) for macOS,
Windows and Linux, plus native iOS and Android apps. `README.md` covers setup
and commands; this file covers the rules.

## Layout

- `src/` is the desktop app, Rust on Rune. It is the only code shared across
  macOS, Windows and Linux.
- `ios/` is a SwiftUI app. XcodeGen generates the Xcode project from
  `ios/project.yml`; edit that file, never the generated `.xcodeproj` or
  `Info.plist`.
- `android/` is a Jetpack Compose app built with the Gradle wrapper.
- Nothing is shared between desktop and mobile, because Rune runs on desktop
  only. Don't add a shared core, an FFI layer, or a cross-platform UI layer
  until a real feature needs the same logic on both.

## Checks

- Run `mise run ci` before every push: rustfmt check, clippy with
  `-D warnings`, and the release build. Run `mise run ios` or
  `mise run android` when you touch those directories. CI runs all of them.
- Read build and lint output whole. Filtering it through `grep` or `head`
  hides the failure you are looking for.
- `unwrap` and `expect` warn, and CI turns warnings into errors: handle the
  error or restructure. `dbg!` is denied. Tests may unwrap, expect, panic and
  `dbg!` (`clippy.toml`).

## Desktop UI

- Rune is the only desktop UI toolkit. Reach for platform code (AppKit, Win32,
  Wayland) only for what Rune doesn't provide.
- Take colours and type from Rune's tokens (`rune::tok`: `theme`, `type_`),
  never literals.
- Write copy in sentence case.
- Verify every visual change with a headless render, then open the PNG and
  look at it:
  - `mise run snap out.png`, or
    `cargo run -- --snap out.png --size 620x520 --scale 2`.
  - `cargo run -- --tree tree.json` writes the element tree: roles, labels,
    bounds.
  - Only debug builds take these flags; release builds leave out Rune's
    tooling. Rune's `crates/rune/README.md`, "Agent tooling", lists the rest
    (scripts, videos, traces).
  - On Linux without a GPU, set
    `VK_ICD_FILENAMES=/usr/share/vulkan/icd.d/lvp_icd.json`.
- A render is not proof of the real window. Check input, focus and window
  behaviour with `mise run dev`.

## Rune

- Rune isn't on crates.io. `Cargo.toml` pins one rev of its `dev` branch.
  Never run `cargo publish`.
- Cargo doesn't apply a dependency's `[patch.crates-io]`, so `Cargo.toml`
  repeats Rune's patched crates (Rune's `crates/rune/README.md`, "Depending on
  Rune").
- To bump Rune:
  1. Read the `## dev` section of Rune's `CHANGELOG.md` since the current pin
     and adapt to its breaking changes.
  2. Set the new rev on `rune` and on every `[patch.crates-io]` entry. Compare
     that list with the `[patch.crates-io]` in Rune's root `Cargo.toml` at the
     new rev, and add or drop entries to match.
  3. Run `cargo update -p rune`, `mise run ci` and `mise run snap`.
- If Rune itself is broken, report it to Rune instead of working around it
  here.

## Mobile

- Each phone app is native: SwiftUI on iOS (Swift 6, iOS 18+), Jetpack
  Compose on Android (minSdk 31, targetSdk 36). Don't bring Rune or a
  cross-platform UI framework to the phones.
- `mise run ios` generates the project and builds for the simulator without
  signing. `mise run android` builds a debug APK with Java 17; Gradle finds the
  SDK through `ANDROID_HOME` or `android/local.properties`.
- The phone apps copy Rune's dark theme colours by hand (`#121314`
  background, `#EFF0F2` text). Update them when Rune's theme changes.
- `com.example.desktoptemplate` is a placeholder app ID.

## Code shape

- Keep source files under 800 lines, not counting tests. 1,500 is a hard cap.
  Split by capability.
- No speculative seams. Add a trait, platform abstraction or plugin point only
  when there are two real implementations or a current test needs the
  substitution. Per-platform modules stay concrete.
