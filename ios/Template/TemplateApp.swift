import SwiftUI

@main
struct TemplateApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

struct ContentView: View {
    var body: some View {
        Text("Hello, world")
            .font(.headline)
            .foregroundStyle(Color(red: 0xEF / 255, green: 0xF0 / 255, blue: 0xF2 / 255))
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(red: 0x12 / 255, green: 0x13 / 255, blue: 0x14 / 255))
    }
}
