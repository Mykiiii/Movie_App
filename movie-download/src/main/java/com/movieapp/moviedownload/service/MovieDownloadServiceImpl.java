package com.movieapp.moviedownload.service;

import com.movieapp.moviedownload.exception.MovieAlreadyDownloadedException;
import com.movieapp.moviedownload.exception.MovieNotFoundException;
import com.movieapp.moviedownload.model.Movie;
import com.movieapp.moviedownload.model.UserMovies;
import com.movieapp.moviedownload.repository.MovieDownloadRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
//import com.movieapp.auth.model.User;

@Service
public class MovieDownloadServiceImpl implements MovieDownloadService {

    @Autowired
    private MovieDownloadRepo downloadRepo;

//	@Autowired
//	private MovieServiceFeignInterface movieServiceFeignInterface;

    @Autowired
    private RestTemplate restTemplate;

    //	UserMovies res=null;
    List<Movie> downloadedMovies;

    UserMovies userMovies;


    @Override
    public boolean updateMovie(int userId, int movieId) throws MovieNotFoundException, MovieAlreadyDownloadedException {
        // TODO Auto-generated method stub
        Optional<UserMovies> moviesdb = downloadRepo.findById(userId);
        if(moviesdb.isEmpty()){
            userMovies = new UserMovies();
            userMovies.setUserId(userId);
            Movie movie = restTemplate.getForObject("http://Movie-service/api/movie/internaluse/get/"+movieId,Movie.class);
            downloadedMovies = new ArrayList<>();
            downloadedMovies.add(movie);
            userMovies.setDownloadedMovies(downloadedMovies);
            userMovies = downloadRepo.save(userMovies);
            return true;
        }else if (moviesdb.isPresent()) {
            Movie movie = restTemplate.getForObject("http://Movie-service/api/movie/internaluse/get/"+movieId,Movie.class);
            downloadedMovies = moviesdb.get().getDownloadedMovies();
            if (downloadedMovies != null) {
                if (downloadedMovies.contains(movie)) {
                    throw new MovieAlreadyDownloadedException("Movie is already downloaded");
                }else{
                    downloadedMovies.add(movie);
                    userMovies.setDownloadedMovies(downloadedMovies);
                    userMovies = downloadRepo.save(userMovies);
                    return true;
                }
            }
            downloadedMovies = new ArrayList<>();
            downloadedMovies.add(movie);
            moviesdb.get().setDownloadedMovies(downloadedMovies);
            userMovies = downloadRepo.save(moviesdb.get());
            if (moviesdb != null) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Movie viewDownloadById(int userId, int movieId) throws MovieNotFoundException {
        UserMovies moviesdb = downloadRepo.findById(userId).get();
        if (moviesdb != null) {
            downloadedMovies = moviesdb.getDownloadedMovies();

            if (downloadedMovies != null) {
                Movie movie = getMovie(downloadedMovies, movieId);
                if (movie != null) {
                    return movie;
                } else {
                    throw new MovieNotFoundException("Movie not found with ID " + movieId);
                }
            } else {
                throw new MovieNotFoundException("Movie not found with ID " + movieId);
            }
        } else {
            throw new MovieNotFoundException("No Downloads found for User ID " + userId);
        }
    }

    @Override
    public List<Movie> viewAllDownloads(int userId) throws MovieNotFoundException {
        // TODO Auto-generated method stub
        UserMovies moviesdb = getUserMovies(userId);

		if(moviesdb!=null) {
			downloadedMovies = moviesdb.getDownloadedMovies();
			if (downloadedMovies != null) {
				return downloadedMovies;
				}else{
					throw new MovieNotFoundException("No Movies Found");
				}
			}else{
			throw new MovieNotFoundException("User not found with ID"+userId);
		}
	}

	@Override
	public void removeAllDownloads(int userId) throws MovieNotFoundException, MovieNotFoundException {
		// TODO Auto-generated method stub
		UserMovies moviesdb = getUserMovies(userId);
		if(moviesdb!=null) {
			downloadedMovies = moviesdb.getDownloadedMovies();
			if(downloadedMovies==null){
				throw new MovieNotFoundException("No Movies Found");
			}
			moviesdb.setDownloadedMovies(null);
			downloadRepo.save(moviesdb);
		}else{
			throw new MovieNotFoundException("User not found with ID"+userId);
		}
	}

    @Override
    public void removeDownloadById(int userId, int movieId) throws MovieNotFoundException {
        // TODO Auto-generated method stub
        UserMovies moviesdb = getUserMovies(userId);
        if (moviesdb != null) {
            downloadedMovies = moviesdb.getDownloadedMovies();
            if (downloadedMovies != null && !(downloadedMovies.isEmpty())) {
                Movie movie = getMovie(downloadedMovies, movieId);
                if (movie != null) {
                    downloadedMovies.remove(movie);
                    moviesdb.setDownloadedMovies(downloadedMovies);
                    downloadRepo.save(moviesdb);
                } else {
                    throw new MovieNotFoundException("Movie not found with ID " + movieId);
                }
            } else {
                throw new MovieNotFoundException("Movie not Found with Id " + movieId);
            }

        } else {
            throw new MovieNotFoundException("User not found with ID " + userId);
        }
    }


    private UserMovies getUserMovies(int userId) {
        return downloadRepo.findById(userId).get();
    }


    private Movie getMovie(List<Movie> moviesList, int movieId) {
        return moviesList.stream().filter(mv -> mv.getMovieId() == movieId).findFirst().get();
    }

    private Movie restTemplateMovie(Optional<UserMovies> moviesdb, int movieId) throws MovieAlreadyDownloadedException {
        Movie movie = restTemplate.getForObject("http://localhost:8083/api/movie/get/{movieId}", Movie.class);
        downloadedMovies = moviesdb.get().getDownloadedMovies();
        if (downloadedMovies != null) {
            if (downloadedMovies.contains(movie)) {
                throw new MovieAlreadyDownloadedException("Movie is already downloaded");
            }
        }
        return movie;
    }

}
