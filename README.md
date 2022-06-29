# ftp-file-appender-batch

---------------------

This project was made to validate filesystem concurrent when multiples instances appent to the same file and validate in the end to check if some append request failed.

The code was made to simple filesystem that can be used to FTP.

## Test FTP

Execute commands from "./local-env/docker" directory.

### Iniciar

```shell
docker-compose up
```

### Remover recursos

```shell
docker-compose down
docker container prune -f 
docker volume prune -f
```
