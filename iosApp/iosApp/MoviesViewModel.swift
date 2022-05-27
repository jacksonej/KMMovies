//
//  MoviesViewModel.swift
//  iosApp
//
//  Created by jackson on 22/10/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import Foundation
import shared

class MoviesViewModel: ObservableObject {
    @Published var people = [Movie]()

    private let repository: MovieRepository
    init(repository: MovieRepository) {
        self.repository = repository
    }
    
    func fetch() {
        repository.getMovies(success:  { data in
            });
    }
}
