FROM node:lts-alpine
RUN npm install -g http-server
WORKDIR /app
COPY package*.json ./
RUN npm install
ENV NODE_OPTIONS="--openssl-legacy-provider"
COPY . .
RUN npm run build
EXPOSE 8766
CMD ["npm", "run", "serve"]
