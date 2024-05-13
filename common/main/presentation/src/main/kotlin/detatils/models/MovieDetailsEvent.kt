package detatils.models



sealed class MovieDetailsEvent  {
    object MovieShow : MovieDetailsEvent()

    object BackClick : MovieDetailsEvent()
}