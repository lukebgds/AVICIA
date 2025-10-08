FROM node:22
WORKDIR /app
COPY package*.json ./
RUN npm install
EXPOSE 8080
RUN npm install -g nodemon
CMD ["npx", "nodemon", "--legacy-watch", "--polling-interval=1000", "--ignore", "node_modules", "--exec", "npm run dev"]
