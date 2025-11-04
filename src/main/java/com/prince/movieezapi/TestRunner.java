package com.prince.movieezapi;

import com.prince.movieezapi.user.models.*;
import com.prince.movieezapi.user.services.MovieEzUserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class TestRunner implements ApplicationRunner {

    private final MovieEzUserService movieEzUserService;

    public TestRunner(MovieEzUserService movieEzUserService) {
        this.movieEzUserService = movieEzUserService;
    }

    @Override
    public void run(ApplicationArguments args) {

        MovieEzUserRoleModel adminRole = MovieEzUserRoleModel.builder().description(MovieEzAppRole.ADMIN).build();
        MovieEzUserRoleModel userRole = MovieEzUserRoleModel.builder().description(MovieEzAppRole.USER).build();
        MovieEzUserModel user1 = MovieEzUserModel.builder()
                .username("prince")
                .email("prince@mail.com")
                .password("Password1!")
                .build();
        MovieEzUserPlaylistModel user1Playlist = MovieEzUserPlaylistModel.builder().name("Favorites").user(user1).build();
        MovieEzPlaylistContentsModel user1PlaylistContent1 = MovieEzPlaylistContentsModel.builder()
                .trackId("abc1")
                .build();

        // Adding Roles
        user1.addRole(adminRole);
        user1.addRole(userRole);

        // Adding Playlist and Contents
        user1.addPlaylist(user1Playlist);
        user1Playlist.addContent(user1PlaylistContent1);

        // Saving User
        movieEzUserService.save(user1);

        MovieEzUserRoleModel adminRole2 = MovieEzUserRoleModel.builder().description(MovieEzAppRole.ADMIN).build();
        MovieEzUserRoleModel userRole2 = MovieEzUserRoleModel.builder().description(MovieEzAppRole.USER).build();
        MovieEzUserModel user2 = MovieEzUserModel.builder()
                .username("horizon")
                .email("horizon@mail.com")
                .password("Password1!")
                .build();
        MovieEzUserPlaylistModel user2Playlist1 = MovieEzUserPlaylistModel.builder().name("Favorites").user(user1).build();
        MovieEzUserPlaylistModel user2Playlist2 = MovieEzUserPlaylistModel.builder().name("LoFi tracks").user(user1).build();
        MovieEzPlaylistContentsModel user2Playlist1Content1 = MovieEzPlaylistContentsModel.builder()
                .trackId("abc1")
                .build();
        MovieEzPlaylistContentsModel user2Playlist2Content1 = MovieEzPlaylistContentsModel.builder()
                .trackId("aaa111")
                .build();
        MovieEzPlaylistContentsModel user2Playlist2Content2 = MovieEzPlaylistContentsModel.builder()
                .trackId("bbb222")
                .build();

        // Adding Roles
        user2.addRole(adminRole2);
        user2.addRole(userRole2);

        // Adding Playlist and Contents
        user2.addPlaylist(user2Playlist1);
        user2.addPlaylist(user2Playlist2);
        user2Playlist1.addContent(user2Playlist1Content1);
        user2Playlist2.addContent(user2Playlist2Content1);
        user2Playlist2.addContent(user2Playlist2Content2);

        // Saving User
        movieEzUserService.save(user2);
    }
}
