import gulp from 'gulp';
import gulpSass from 'gulp-sass';
import sassCompiler from 'sass';
import sourcemaps from 'gulp-sourcemaps';
import autoprefixer from 'gulp-autoprefixer';
import cleanCSS from 'gulp-clean-css';

const sass = gulpSass(sassCompiler);

function styles() {
  return gulp.src('src/scss/**/*.scss')
    .pipe(sourcemaps.init())
    .pipe(sass().on('error', sass.logError))
    .pipe(autoprefixer())
    .pipe(cleanCSS())
    .pipe(sourcemaps.write('.'))
    .pipe(gulp.dest('src/css'));
}

function watch() {
  gulp.watch('src/scss/**/*.scss', styles);
}

export { styles, watch };
export default gulp.series(styles, watch);