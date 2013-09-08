package audio;

public class AudioObject {

    int id;
    int ownerId;
    String artist;
    String title;
    int duration;
    String url;
    String lyricsId;
    int albumId;
    int genreId;

    public int getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getArtist() {
        return artist;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getUrl() {
        return url;
    }

    public String getLyricsId() {
        return lyricsId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public int getGenreId() {
        return genreId;
    }

    @Override
    public String toString() {
        return artist + " - " + title;
    }

}
