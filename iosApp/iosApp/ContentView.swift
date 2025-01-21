import SwiftUI
import Shared

struct ContentView: View {
    @StateObject
    var viewModel = HomeViewModel()
    
    var body: some View {
        VStack {
            if viewModel.response?.isSuccess() == true  {
                ScrollView {
                    LazyVStack(spacing: 16) {
                        ForEach(viewModel.response?.getMovies() ?? [], id: \.id) { element in
                            MovieView(movie: element)
                                .padding()
                                .background(Color(.systemGray6))
                                .cornerRadius(8)
                                .shadow(radius: 2)
                        }
                    }
                    .padding(.horizontal)
                }
            } else if viewModel.response?.isError() == true {
                VStack {
                    Spacer()
                    Text("\(viewModel.response?.getErrorMessage() ?? "Unknown Error")")
                        .font(.title3)
                        .fontWeight(.bold)
                    Spacer()
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            } else if viewModel.response?.isLoading() == true {
                VStack {
                    Spacer()
                    ProgressView("Loading")
                        .progressViewStyle(CircularProgressViewStyle())
                        .padding()
                    Spacer()
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }.task {
            await viewModel.fetchMovie()
        }
    }
}

class HomeViewModel: ObservableObject {
    @Published
    private(set) var response: RequestState? = nil
    
    @MainActor
    func fetchMovie() async {
        for await requestState in MoviesRepositoryImpl(moviesApi: MoviesApi()).fetchPopularMovies() {
            response = requestState
        }
    }
}
