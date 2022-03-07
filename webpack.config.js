const webpack = require('webpack');
const path = require('path');

module.exports = {
  entry: './src/main/js/index.jsx',
  cache: true,
  mode: 'production',
  devtool: 'source-map',

  output: {
    path: __dirname,
    filename: './src/main/resources/static/built/bundle.js',
  },

  module: {
    rules: [
      {
        test: path.join(__dirname, '.'),
        exclude: /(node_modules)/,
        use: [
          {
            loader: 'babel-loader',
            options: {
              presets: ['@babel/preset-env', '@babel/preset-react'],
            },
          },
        ],
      },
    ],
  },

  resolve: {
    extensions: ['.js', '.jsx'],
  },

  plugins: [
    new webpack.HotModuleReplacementPlugin(),
    new webpack.ProvidePlugin({
      React: 'react',
    }),
  ],
};
