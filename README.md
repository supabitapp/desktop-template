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
```

Rune isn't on crates.io; `Cargo.toml` pins one git rev of its `dev` branch.
Bump it by changing `rev` and running `cargo update -p rune`. Bump Rust by
changing `rust` in `mise.toml`.

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
