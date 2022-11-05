package com.movieapp.moviefav.service;


import com.movieapp.moviefav.exception.MovieAlreadyFavoriteException;
import com.movieapp.moviefav.exception.NoFavoriteFoundException;
import com.movieapp.moviefav.exception.MovieNotFoundException;
//import com.movieapp.moviefav.feignInterface.MovieServiceFeignInterface;
import com.movieapp.moviefav.model.Movie;
import com.movieapp.moviefav.model.UserMovies;
import com.movieapp.moviefav.repository.MovieFavoriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieFavoriteServiceImpl implements MovieFavoriteService {

    @Autowired
    private MovieFavoriteRepo movieFavoriteRepo;

//	@Autowired
//	private MovieServiceFeignInterface movieServiceFeignInterface;

    //	UserMovies res=null;
    List<Movie> favMovies;

    @Autowired
    private RestTemplate restTemplate;
    UserMovies userMovies;

    @Override
    public boolean updateMovie(int userId, int movieId) throws MovieNotFoundException, MovieAlreadyFavoriteException {
        Optional<UserMovies> user = movieFavoriteRepo.findById(userId);
        Movie movie = restTemplate.getForObject("https://Movie-Service/api/movie/internaluse/get/"+movieId,Movie.class);
        if(movie == null){
            throw new MovieNotFoundException("Movie Not found with ID "+movieId);
        }
        if (user.isPresent()) {
            if(CollectionUtils.isEmpty(user.get().getFavMovies())  )
            {
                user.get().setFavMovies(List.of(movie));
                userMovies = movieFavoriteRepo.save(user.get());
                return true;
            }
            else if(user.get().getFavMovies().contains(movie)){
                throw new MovieAlreadyFavoriteException("Movie is already downloaded");
            }
            else if(!user.get().getFavMovies().contains(movie)){
                favMovies=user.get().getFavMovies();
                favMovies.add(movie);
                userMovies = new UserMovies();
                userMovies.setUserId(userId);
                userMovies.setFavMovies(favMovies);
                userMovies = movieFavoriteRepo.save(userMovies);
                return true;
            }
        }else {
            favMovies=new ArrayList<>();
            favMovies.add(movie);
            userMovies = new UserMovies();
            userMovies.setUserId(userId);
            userMovies.setFavMovies(favMovies);
            userMovies = movieFavoriteRepo.save(userMovies);
            return true;
        }
        return false;
    }


    @Override
    public Movie viewFavoriteById(int userId, int movieId) throws MovieNotFoundException, NoFavoriteFoundException {
        UserMovies moviesdb = movieFavoriteRepo.findById(userId).get();
        if (moviesdb != null) {
            favMovies = moviesdb.getFavMovies();
            if (favMovies != null) {
                Movie movie = getMovie(favMovies, movieId);
                if (movie != null) {
                    return movie;
                } else {
                    throw new NoFavoriteFoundException("Favorite Movie not found with ID" + movieId);
                }
            } else {
                throw new NoFavoriteFoundException("Favorites not found for userId" + userId);
            }
        } else {
            throw new MovieNotFoundException("User not found with ID" + userId);
        }
    }

    @Override
    public List<Movie> viewAllFavorites(int userId) throws NoFavoriteFoundException, MovieNotFoundException {
        // TODO Auto-generated method stub
        UserMovies moviesdb = getUserMovies(userId);

        if (moviesdb != null) {
            favMovies = moviesdb.getFavMovies();
            if (favMovies != null) {
                return favMovies;
            } else {
                throw new NoFavoriteFoundException("Favorites not found for userId" + userId);
            }
        } else {
            throw new MovieNotFoundException("User not found with ID" + userId);
        }
    }

    @Override
    public void removeAllFavorites(int userId) throws MovieNotFoundException, NoFavoriteFoundException {
        // TODO Auto-generated method stub
        UserMovies moviesdb = getUserMovies(userId);
        if (moviesdb != null) {
            favMovies = moviesdb.getFavMovies();
            if (favMovies == null) {
                throw new NoFavoriteFoundException("Favorites not found for userId" + userId);
            }
            moviesdb.setFavMovies(null);
            movieFavoriteRepo.save(moviesdb);
        } else {
            throw new MovieNotFoundException("User not found with ID" + userId);
        }
    }

    @Override
    public void removeFavoriteById(int userId, int movieId) throws NoFavoriteFoundException, MovieNotFoundException {
        // TODO Auto-generated method stub
        UserMovies moviesdb = getUserMovies(userId);
        if (moviesdb != null) {
            favMovies = moviesdb.getFavMovies();
            Movie movie = getMovie(favMovies, movieId);
            if (movie != null) {
                favMovies.remove(movie);
                moviesdb.setFavMovies(favMovies);
                movieFavoriteRepo.save(moviesdb);
            } else {
                throw new NoFavoriteFoundException("Favorite Movie not found with ID" + movieId);
            }
        } else {
            throw new MovieNotFoundException("User not found with ID" + userId);
        }
    }


    private UserMovies getUserMovies(int userId) {
        return movieFavoriteRepo.findById(userId).get();
    }


    private Movie getMovie(List<Movie> moviesList, int movieId) {
        return moviesList.stream().filter(mv -> mv.getMovieId() == movieId).findFirst().get();
    }

}
