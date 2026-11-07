# desktop-template

The smallest cross-platform desktop app on [Rune](https://github.com/anomalyco/rune),
a Rust GPU UI toolkit (wgpu + winit). One window, one line of text, on macOS,
Windows, and Linux (X11 and Wayland). Its only dependency is `rune`.

```sh
cargo run                               # open the window
cargo run -- --snap hello.png           # render headless to a PNG
```

Rune isn't on crates.io; `Cargo.toml` pins one git rev of its `dev` branch.
Bump it by changing `rev` and running `cargo update -p rune`.

On Linux without a GPU, render on Mesa's lavapipe:
`VK_ICD_FILENAMES=/usr/share/vulkan/icd.d/lvp_icd.json cargo run -- --snap hello.png`.

Windows builds link the C runtime statically (`.cargo/config.toml`), so the
executable runs without the Visual C++ Redistributable.

CI builds a release executable on all three OSes and uploads each one's
headless render as an artifact.
