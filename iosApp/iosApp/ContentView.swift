import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
      ComposeView()
    }
}

#Preview {
    ContentView()
}

struct ComposeView: UIViewControllerRepresentable {
  
  func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {}
  
  func makeUIViewController(context: Context) -> some UIViewController {
    let context = DefaultComponentContext(lifecycle: ApplicationLifecycle())
    let rootComponent: RootComponent = RootViewKt.getRootFactory()
      .create(componentContext: context)
    return RootViewKt.mainViewController(rootComponent: rootComponent)
  }
}
