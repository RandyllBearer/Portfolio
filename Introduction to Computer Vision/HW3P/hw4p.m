%Randyll Bearer: HW4P: Image Descriptions
%----------------------------------
%Part 1: Feature Description:
testImage = imread('leopard1.jpg');
[testImageX, testImageY, testImageZ] = size(testImage);
[x, y, scores, Ih, Iv] = extract_keypoints(testImage);
[features, x, y, scores] = compute_features(x, y, scores, Ih, Iv);

%------------------------------------
%Part 2.
%Was Unable to Complete

%------------------------------------
%Part 3.
%Was Unable to Complete

%end of file
