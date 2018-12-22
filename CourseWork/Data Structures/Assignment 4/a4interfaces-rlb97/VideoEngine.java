
package videoengine;

/**
 * This abstract data type is a predictive engine for video ratings in a streaming video system. It
 * stores a set of users, a set of videos, and a set of ratings that users have assigned to videos.
 */
public interface VideoEngine {

    /**
     * The abstract methods below are declared as void methods with no parameters. You need to
     * expand each declaration to specify a return type and parameters, as necessary. You also need
     * to include a detailed comment for each abstract method describing its effect, its return
     * value, any corner cases that the client may need to consider, any exceptions the method may
     * throw (including a description of the circumstances under which this will happen), and so on.
     * You should include enough details that a client could use this data structure without ever
     * being surprised or not knowing what will happen, even though they haven't read the
     * implementation.
     */

    /**
     * Adds a new video to the system. If a video with the same title already exists within the system
	 * the method will throw an IllegalArgumentException.
	 * 
	 * @param videoToAdd video object being added to the system.
	 * @throws IllegalArgumentException when videoToAdd already exists within the system.
	 * @throws NullPointerException when videoToAdd is equal to null.
     */
    public void addVideo(Video videoToAdd);

    /**
     * Removes an existing video from the system and returns it. If videoToRemove cannot be found within the system
	 * then this method will throw an IllegalArgumentException.  If videoToRemove is a TvEpisode contained
	 * in a TvSeries, videoToRemove will be removed from the TvSeries as well as the system.
	 * If the video had an existing userRating or predictedRating, that rating will be permanently removed along with the video.
	 * 
	 * @param videoToRemove The Video object being removed from the system.
	 * @return Video The Video object removed from the system.
	 * @throws IllegalArgumentException When videoToRemove cannot be found within the system.
	 * @throws NullPointerException When videoToRemove is equal to null.
     */
    public Video removeVideo(Video videoToRemove);

    /**
     * Adds an existing television episode to an existing television series. If either showToAdd or seriesToAddTo cannot
	 * be found within the system, this method will throw an IllegalArgumentException. If showToAdd is already a member of
	 * an existing TvSeries then the method will return False. 
	 * 
	 * @param showToAdd the TvEpisode being added to the TvSeries container.
	 * @param seriesToAddTo the desired TvSeries collection to be added to.
	 * @return boolean True if addToSeries is successful / False if showToAdd is already a member of some other TvSeries.
	 * @throws IllegalArgumentException When either showToAdd or seriesToAddTo do not exist within the system.
	 * @throws NullPointerException when either showToAdd or seriesToAddTo are set to null.
     */
    public boolean addToSeries(TvEpisode showToAdd, TvSeries seriesToAddTo);

    /**
     * Removes a television episode from a television series. If either showToRemove or seriesToRemoveFrom cannot be
	 * found within the system, this method will throw an IllegalArgumentException.  If showToAdd is not found within
	 * the collection seriesToRemoveFrom, this method will return false. 
	 *
	 * @param showToRemove the TvEpisode being removed from the collection seriesToRemoveFrom.
	 * @param seriesToRemoveFrom the TvSeries collection containing the TvEpisode showToRemove.
	 * @return boolean True if showToRemove was successfully removed from seriesToRemoveFrom / False if showToRemove is not contained within seriesToRemoveFrom.
	 * @throws IllegalArgumentException when either showToRemove or seriesToRemoveFrom cannot be found within the system.
	 * @throws NullPointerException when either showToRemove or seriesToRemoveFrom are set to null.
     */
    public boolean removeFromSeries(TvEpisode showToRemove, TvSeries seriesToRemoveFrom);

    /**
     * Sets a user's rating for a video, as a number of stars from 1 to 5. If the video has already been rated [whether through
	 * rateVideo() or predictRating()] the new rating will override. If(rating >5 || rating <1) then 
	 * an IllegalArgumentException will be thrown.  If either user or videoToRate cannot be found within the system, then this method 
	 * will throw an IllegalArgumentException.
	 * 
	 * @param user The User object who is rating videoToRate.
	 * @param videoToRate The Video object being rated.
	 * @param rating The score determined by user to be applied to videoToRate.
	 * @throws IllegalArgumentException when rating does not fall within bounds / videoToRate or user are not found within the system.
	 * @throws NullPointerException when either user or videoToRate are set to null.
     */
    public void rateVideo(User user, Video videoToRate, int rating);

    /**
     * Clears a user's rating on a video. If this user has rated this video and the rating has not
     * already been cleared, then the rating is cleared and the state will appear as if the rating
     * was never made. If this user has not rated this video, or if the rating has already been
     * cleared, then this method will throw an IllegalArgumentException.
     *
     * @param theUser user whose rating should be cleared.
     * @param theVideo video from which the user's rating should be cleared.
     * @throws IllegalArgumentException if the user does not currently have a rating on record for
     * the video.
     * @throws NullPointerException if either the user or the video is null.
     */
    public void clearRating(User theUser, Video theVideo);

    /**
     * Predicts the rating a user will assign to a video that they have not yet rated, as a number
     * of stars from 1 to 5, will be returned as a double as to allow for a null return value. 
	 * If videoTopredict has already been rated by userPredicting through rateVideo() then throw an IllegalArgumentException.
	 * If videoToPredict has already been rated through the use of predictRating() then a current/updated/fresh rating
	 * will be predicted again. If userPredicting has no friends who have rated videoToPredict, return null.
	 * If either userPredicting or videoToPredict cannot be found within the system, then this method will throw an IllegalArgumentException.
	 *
	 * @param userPredicting The User who needs a predicted rating.
	 * @param videoToPredict the Video which is having a rating predicted for it.
	 * @return double the average rating of videoToPredict from userPredicting's FriendsList
	 * @throws IllegalArgumentException when either userPredicting or videoToPredict cannot be found in the system / videoToPredict has already been directly rated by user.
	 * @throws NullPointerException when either userPredicting or videoToPredict are set to null.
     */
    public double predictRating(User userPredicting, Video videoToPredict);

    /**
     * Suggests a video for a user based on their predicted rating, should return highest predictedRating video.
	 * If theUser is not found within the system, the method will throw an IllegalArgumentException. If two or more
	 * Videos have equal predicted ratings then the Video with the most ratings will be suggested, if another tie occurs,
	 * will return by alphabetical order. If theUser has NO predicted ratings, the method will return null. 
	 *
	 * @param theUser the User object which is being suggested a Video.
	 * @return Video the Video object which has been suggested for theUser / null if no predictedRatings are available.
	 * @throws IllegalArgumentException when theUser has not been found within the system.
	 * @throws NullPointerException when theUser is set to null.
     */
    public Video suggestVideo(User theUser);


}

