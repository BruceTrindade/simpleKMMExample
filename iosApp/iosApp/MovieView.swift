//
//  ProductView.swift
//  iosApp
//
//  Created by bruce trindade on 11/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import Shared
import SwiftUI

struct MovieView: View {
    let movie: Movie
    @State private var showAlert: Bool = false

    var body: some View {
        VStack(alignment: .leading) {
            AsyncImage(url: URL(string: "https://image.tmdb.org/t/p/w500\(movie.posterPath ?? "")")) { phase in
                switch phase {
                case .success(let image):
                    image
                        .resizable()
                        .scaledToFill()
                        .frame(maxWidth: .infinity)
                        .clipped()
                        .accessibility(hidden: false)
                        .accessibilityLabel(Text("Product Thumbnail"))
                case .failure(_):
                    VStack {
                        Text("Image not available.")
                            .font(.title3)
                    }
                case .empty:
                    ProgressView()
                        .frame(maxWidth: .infinity)
                @unknown default:
                    EmptyView()
                }
            }

            Text(movie.title ?? "")
                .multilineTextAlignment(.leading)
                .lineLimit(2)
                .truncationMode(.tail)
                .font(.title2.bold())
                .padding(.top, 10)

            Text(movie.overview ?? "")
                .multilineTextAlignment(.leading)
                .lineLimit(6)
                .truncationMode(.tail)
                .font(.body)
                .padding(.top, 5)
                .padding(.bottom, 10)
        }
        .onTapGesture {
            showAlert = true
        }

        .alert(isPresented: $showAlert) {
            Alert(
                title: Text(movie.title ?? "Details").font(.title2.bold()),
                message: Text(movie.overview ?? "No description available.").font(.body),
                dismissButton: .default(Text("Close"))
            )
        }
    }
}
