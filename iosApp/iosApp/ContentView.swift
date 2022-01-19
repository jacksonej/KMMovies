import SwiftUI
import shared
import Combine

struct ContentView: View {
	let greet = Greeting().greeting()
   // @ObservedObject var peopleInSpaceViewModel = MoviesViewModel(repository: MovieRepository())
    
    @State var movies : [Movie]  = []
    @State var uiImage : UIImage? = UIImage()
    @State var refresh: Bool = false
    private var colors: [Color] = [.yellow, .purple, .green]

    private var gridItemLayout = [GridItem(.flexible()), GridItem(.flexible()), GridItem(.flexible())]
    
    var body: some View {
        NavigationView {
            ScrollView {
                
                LazyVGrid(columns: gridItemLayout, spacing: 10) {
                        ForEach(movies, id: \.id) {
                            movie in
                            VStack {
                                ImageView(withURL: ("https://image.tmdb.org/t/p/w185"+movie.posterPath))
                                Text(movie.title).font(.headline).lineLimit(1)
                            }
                        }
                }.padding()
                }
                .navigationBarTitle(Text("Movies "), displayMode: .large)
                .onAppear ( perform: {
                    let repo = MovieRepository()
                    repo.getMovies(success:  { data in
                                            DispatchQueue.main.async {
                                            self.movies = data.results
                                            }
                    });
                    
                    repo.getImage(url: "https://image.tmdb.org/t/p/w185/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg", success: { image in
                        DispatchQueue.main.async {
                                 uiImage = image
                                  }
                    }, failure: { error in
                                  print(error?.description() ?? "")
                    })
                }
               
                );
        }
    }
}


struct ContentView_Previews: PreviewProvider {
	static var previews: some View {
	ContentView()
	}
}

struct ImageView: View {
    @ObservedObject var imageLoader: ImageLoader
    @State var image:UIImage = UIImage()

    init(withURL url:String) {
        imageLoader = ImageLoader(urlString:url)
    }

    var body: some View {
    
        Image(uiImage: imageLoader.image ?? UIImage() )
                        .resizable()
                        .scaledToFill()
                        .frame(width: 120, height: 200, alignment: .center)
            .cornerRadius(8.0)
            .clipped()
                        
    }
}


class ImageLoader: ObservableObject {
    @Published var image: UIImage?
    var urlString: String?
    var imageCache = ImageCache.getImageCache()
    
    init(urlString: String?) {
        self.urlString = urlString
        loadImage()
    }
    
    func loadImage() {
        if loadImageFromCache() {
            print("Cache hit")
            return
        }
        
        print("Cache miss, loading from url")
        loadImageFromUrl()
    }
    
    func loadImageFromCache() -> Bool {
        guard let urlString = urlString else {
            return false
        }
        
        guard let cacheImage = imageCache.get(forKey: urlString) else {
            return false
        }
        
        image = cacheImage
        return true
    }
    
    func loadImageFromUrl() {
        guard let urlString = urlString else {
            return
        }
        
        let url = URL(string: urlString)!
        let task = URLSession.shared.dataTask(with: url, completionHandler: getImageFromResponse(data:response:error:))
        task.resume()
    }
    
    
    func getImageFromResponse(data: Data?, response: URLResponse?, error: Error?) {
        guard error == nil else {
            print("Error: \(error!)")
            return
        }
        guard let data = data else {
            print("No data found")
            return
        }
        
        DispatchQueue.main.async {
            guard let loadedImage = UIImage(data: data) else {
                return
            }
            
            self.imageCache.set(forKey: self.urlString!, image: loadedImage)
            self.image = loadedImage
        }
    }
}


class ImageCache {
    var cache = NSCache<NSString, UIImage>()
    
    func get(forKey: String) -> UIImage? {
        return cache.object(forKey: NSString(string: forKey))
    }
    
    func set(forKey: String, image: UIImage) {
        cache.setObject(image, forKey: NSString(string: forKey))
    }
}

extension ImageCache {
    private static var imageCache = ImageCache()
    static func getImageCache() -> ImageCache {
        return imageCache
    }
}


//VStack {
//    Image(uiImage: uiImage ?? UIImage())
//                .resizable()
//                .scaledToFill()
//                .cornerRadius(10)
//                .frame(width: 120.0, height: 200.0)
//    Spacer().frame(height: 20)
//}
