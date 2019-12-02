import java.util.HashMap;

public class MovieGenre
{
    private static HashMap<Long, String> genre;

    private MovieGenre()
    {
        genre = new HashMap<Long, String>();

        genre.put( 28L , "액션");
        genre.put( 12L, "모험");
        genre.put( 16L, "애니메이션");
        genre.put( 35L, "코미디");
        genre.put( 80L, "범죄");
        genre.put( 99L, "다큐멘터리");
        genre.put( 18L, "드라마");
        genre.put( 10751L, "가족");
        genre.put( 14L, "판타지");
        genre.put( 36L, "역사");
        genre.put( 27L, "공포");
        genre.put( 10402L, "음악");
        genre.put( 9648L, "미스터리");
        genre.put( 10749L, "로맨스");
        genre.put( 878L, "SF");
        genre.put( 10770L, "TV 영화");
        genre.put( 53L, "스릴러");
        genre.put( 10752L, "전쟁");
        genre.put( 37L, "서부");
    }

    public static HashMap<Long,String> getGenre()
    {
        if(genre == null)
            new MovieGenre();

        return genre;
    }
}
