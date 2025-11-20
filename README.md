Movie EZ API
============

The backend service for [movie-ez](https://github.com/VGITA777/movie-ez)

What this service provides

- Media discovery and search endpoints under `/media/**`
- Movie and TV series details, credits, and related resources
- User registration, authentication, and simple playlist management
- Request caching and rate limiting to protect upstream APIs
- A production-friendly setup with Docker and Gradle

# TODO

- [ ] Update rate-limiting to be Role-based instead of the default implemented by Slf4j.
- [x] Update the /media/** endpoint controllers to use the @ModelAttribute annotation.