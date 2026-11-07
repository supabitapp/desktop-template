use rune::tok::{self, type_};
use rune::{AppSpec, Cx, El, col, text};

struct State;

fn view(_: &State, _: &mut Cx<State>) -> El {
    let t = tok::theme(true);
    col()
        .w_full()
        .h_full()
        .center()
        .fill(t.bg)
        .child(text("Hello, world").font(type_::TITLE).color(t.text))
}

fn main() {
    rune::run(AppSpec::new("Desktop Template", (480.0, 320.0), || State, view));
}
