//
//  ContentView.swift
//  iosApp
//
//  Created by Beknur Patsaev on 06.12.2024.
//

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
  func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
    
  }
  
  func makeUIViewController(context: Context) -> some UIViewController {
    RootViewKt.mainViewController()
  }
}
