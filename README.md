# desktop-template

The smallest cross-platform desktop app on [Rune](https://github.com/anomalyco/rune),
a Rust GPU UI toolkit (wgpu + winit). One window, one line of text, on macOS,
Windows, and Linux (X11 and Wayland). Its only dependency is `rune`.

[mise](https://mise.jdx.dev) installs the pinned Rust toolchain and
[mr boxington](https://mr-boxington.jdx.dev), a shared compile cache that
`cargo` runs through, so other checkouts and worktrees reuse compiled crates.

```sh
mise install          # Rust 1.99.1 with clippy and rustfmt, plus mbx
mise run dev          # open the window
mise run snap         # render headless to hello.png
mise run lint         # rustfmt check and clippy with -D warnings
mise run ci           # everything CI runs
mise run ios          # build the iOS app for the simulator
mise run android      # build the Android debug APK
```

Rune isn't on crates.io; `Cargo.toml` pins one git rev of its `dev` branch.
Cargo doesn't apply a dependency's `[patch.crates-io]`, so `Cargo.toml`
repeats Rune's patched crates (winit, wgpu-hal, and others) at the same rev.
To bump, change every `rev`, compare the `[patch.crates-io]` list with Rune's
own `Cargo.toml` at that rev, and run `cargo update -p rune`. Bump Rust by
changing `rust` in `mise.toml`.

Release builds leave out Rune's headless tooling (`--snap` and friends), so
`mise run snap` renders from a debug build.

On Linux without a GPU, render on Mesa's lavapipe:
`VK_ICD_FILENAMES=/usr/share/vulkan/icd.d/lvp_icd.json cargo run -- --snap hello.png`.

Windows builds link the C runtime statically (`.cargo/config.toml`), so the
executable runs without the Visual C++ Redistributable.

CI installs the same tools through `jdx/mise-action`, restores the compile
cache with `jdx/mr-boxington-action` (pushes to `main` save it, pull requests
only restore), runs `mise run ci` on all three OSes, and uploads each one's
headless render as an artifact.

Linux builds link `libxkbcommon` (`libxkbcommon-dev` on Debian/Ubuntu,
`libxkbcommon` on Arch).

## Mobile

Rune runs on desktop only, so the phone apps are native, the same "Hello,
world" in Rune's dark theme colors. Nothing is shared with the desktop app.

- `ios/` is a SwiftUI app. [XcodeGen](https://github.com/yonaskolb/XcodeGen)
  generates `Template.xcodeproj` from `project.yml`; edit `project.yml`, not
  the project. `mise run ios` installs XcodeGen and builds for the simulator
  without signing. Open the generated project in Xcode to run it.
- `android/` is a Jetpack Compose app built with the Gradle wrapper.
  `mise run android` installs Java 17 and builds a debug APK. Gradle finds the
  Android SDK through `ANDROID_HOME` or `android/local.properties`
  (Android Studio writes it).

Both use the placeholder ID `com.example.desktoptemplate`. CI builds each on
every push and pull request.
