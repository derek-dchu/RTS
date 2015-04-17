'use strict';

var gulp = require('gulp'),
    notify = require('gulp-notify'),    
    jshint = require('gulp-jshint'),
    rename = require('gulp-rename'),
    concat = require('gulp-concat'),
    uglify = require('gulp-uglify'),
    minifycss = require('gulp-minify-css'),
    ngAnnotate = require('gulp-ng-annotate');


gulp.task('default', function() {
  gulp.start('css', 'scripts');
});

gulp.task('css', function() {
    return gulp.src(['css/index.css', 'css/loading-spinner.css', 'css/sidebar.css'])
        .pipe(concat('index.css'))
        .pipe(gulp.dest('dist/css'))
        .pipe(rename({suffix: '.min'}))
        .pipe(minifycss())
        .on('error', function (err) { console.log(err.message); })
        .pipe(gulp.dest('dist/css'))
        .pipe(notify({message: 'Css task complete'}))
});

gulp.task('scripts', function() {
  return gulp.src(['js/google-chart.js', 'js/sidebar.js', 'js/index.js', 'bower_components/card/lib/js/card.js'])
    .pipe(jshint('.jshintrc'))
    .pipe(jshint.reporter('default'))
    .pipe(ngAnnotate({ remove:true, add:true, single_quotes:true }))
    .pipe(concat('index.js'))
    .pipe(gulp.dest('dist/js'))
    .pipe(rename({suffix: '.min'}))
    .pipe(uglify())
    .on('error', function (err) { console.log(err.message); })
    .pipe(gulp.dest('dist/js'))
    .pipe(notify({ message: "Scripts task complete" }));
});