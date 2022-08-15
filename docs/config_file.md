# Explanation of the config.yml file
```yaml
thread-pool-size: 5
extension-actions: true
join-command-method: true
```

- <b>thread-pool-size</b>: The thread-pool is used for map-reset after finishing a game, in this case the number of threads that the game will have available to execute map-reset must be indicated. This depends on the memory where the server is running and the number of maps/games the server runs, it is recommended to leave it at 5, but if necessary you can increase the size.
- <b>extension-actions</b>: If it is true it will load extensions and api methods, if it is false it will only allow the basics.
- <b>join-command-method</b>: If it is true, /skywars command will be added, which when executed will find an available game to enter. Normally only use in developer mode is recommended.