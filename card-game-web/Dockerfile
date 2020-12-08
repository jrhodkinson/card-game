FROM node:14.15.1-alpine as build
WORKDIR /app
ENV PATH /app/node_modules/.bin:$PATH
COPY package.json ./
COPY yarn.lock ./
RUN npx browserslist@latest --update-db
RUN yarn install --frozen-lockfile
COPY . ./
RUN yarn build

FROM nginx:1.19
COPY --from=build /app/build/ /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
