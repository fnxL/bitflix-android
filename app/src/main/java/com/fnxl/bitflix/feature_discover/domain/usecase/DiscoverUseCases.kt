package com.fnxl.bitflix.feature_discover.domain.usecase

data class DiscoverUseCases(
    val getRowData: GetRowDataUseCase,
    val getTrending: GetTrendingUseCase,
    val getMovieDetails: GetMovieDetailsUseCase,
    val getShowDetails: GetShowDetailsUseCase,
    val getSeasonDetails: GetSeasonDetailsUseCase,
    val getStreamLinksUseCase: GetStreamLinksUseCase,
    val searchTitleUseCase: SearchTitleUseCase
)
