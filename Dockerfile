#Use a node base image
FROM node:16

#Set maintainer
LABEL maintainer "satyamtiwari210@gmail.com"

#Set a health check
HEALTHCHECK --interval=30s \
    --timeout=30s \
    CMD curl -f http://127.0.0.1:8000 || exit 1

EXPOSE 8000