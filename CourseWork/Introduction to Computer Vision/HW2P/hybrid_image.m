%Randyll Bearer: Programming Homework 2: Part 3 Hybrid Images

%read in woman-happy/woman-neutral
im1 = imread('woman_happy.png');
im2 = imread('woman_neutral.png');

%convert images to grayscale
im1 = rgb2gray(im1);
im2 = rgb2gray(im2);

%resize both images to standard 512x512
im1 = imresize(im1, [512 512]);
im2 = imresize(im2, [512 512]);

%apply gaussian filter to both and store the results
im1_blur = imgaussfilt(im1, 10, 'FilterSize', 31);
im2_blur = imgaussfilt(im2, 10, 'FilterSize', 31);

%obtain the detail image (close up)
im2_detail = im2 - im2_blur;

%combine im2_detail with im1_blur
hybrid = im1_blur + im2_detail;

%display hybrid image and save it
figure
imshow(hybrid);
saveas(gcf, 'hybrid.png');