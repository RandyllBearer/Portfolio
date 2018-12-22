function [features, x, y, scores] = compute_features(x, y, scores, Ix, Iy);

%1. Sanitize (x,y) inputs (<5 top/left/right/bottom)
numDeleted = 0; %for testing purposes, will record how many keypoints were deleted
[testImageX, testImageY] = size(Ix); %dimensions of the image
[numKeypoints, unimportant] = size(x); %used for the indexing limit in loops

i = 1;
while i <= numKeypoints %loop through every element in x/y
   if x(i) <= 5     %Delete every keypoint within 5 pixels of the left
       x(i) = [];
       y(i) = [];
       scores(i) = [];
       numDeleted = numDeleted + 1;
       numKeypoints = numKeypoints - 1; %Don't want to outrun matrix bounds
   elseif x(i) > (testImageX-5) %Delete every keypoint within 5 pixels of the right
       x(i) = [];
       y(i) = [];
       scores(i) = [];
       numDeleted = numDeleted + 1;
       numKeypoints = numKeypoints - 1;
   elseif y(i) <= 5 %Delete every keypoint within 5 pixels of the top
       x(i) = [];
       y(i) = [];
       scores(i) = [];
       numDeleted = numDeleted + 1;
       numKeypoints = numKeypoints - 1;
   elseif y(i) > (testImageY-5) %Delete every keypoint within 5 pixels of the bottom
       x(i) = [];
       y(i) = [];
       scores(i) = [];
       numDeleted = numDeleted + 1;
       numKeypoints = numKeypoints - 1;
   else
       i = i + 1;
   end
end

%2. Compute gradient magnitude and gradient angle at each keypoint
gradientMagnitude = zeros(numKeypoints); %To store computed magnitudes
gradientAngle = zeros(numKeypoints);     %to store computed angles

for i = 1:numKeypoints
    gradientMagnitude(i) = sqrt( (Ix( x(i)+1 )-Ix( x(i)-1 ))^2 + (Iy( y(i)+1 )-Iy( y(i)-1 ))^2);
    if gradientMagnitude(i) == 0
       %Think about optimizing as described
    end
    gradientAngle(i) = atand(( Iy( y(i)+1 ) - Iy( y(i)-1 ))/( Ix(x(i)+1 ) - Ix( x(i)-1 )));
end

%3. Quantize the gradient orientations into 8 bins
gradientOrientation = zeros(numKeypoints);
sumMagnitudes = zeros(1,8); %Cumulative magnitudes of points in bin1 = sumMagnitudes(1)

for i= 1:numKeypoints
   if gradientAngle(i) >= -90 & gradientAngle(i) <= -67.5  %bin 1
       gradientOrientation(i) = 1;
        sumMagnitudes(1) = sumMagnitudes(1) + gradientMagnitude(i);

   elseif gradientAngle(i) > -67.5 & gradientAngle(i) <= -45 %bin2
        gradientOrientation(i) = 2;
        sumMagnitudes(2) = sumMagnitudes(2) + gradientMagnitude(i);

   elseif gradientAngle(i) > -45 & gradientAngle(i) <= -22.5 %bin 3
        gradientOrientation(i) = 3;
        sumMagnitudes(3) = sumMagnitudes(3) + gradientMagnitude(i);

   elseif gradientAngle(i) > -22.5 & gradientAngle(i) <= 0 %bin 4
        gradientOrientation(i) = 4;
        sumMagnitudes(4) = sumMagnitudes(4) + gradientMagnitude(i);

   elseif gradientAngle(i) > 0 & gradientAngle(i) <= 22.5 %bin 5
        gradientOrientation(i) = 5;
        sumMagnitudes(5) = sumMagnitudes(5) + gradientMagnitude(i);

   elseif gradientAngle(i) > 22.5 & gradientAngle(i) <= 45 %bin 6
        gradientOrientation(i) = 6;
        sumMagnitudes(6) = sumMagnitudes(6) + gradientMagnitude(i);

   elseif gradientAngle(i) > 45 & gradientAngle(i) <= 67.5 %bin 7
        gradientOrientation(i) = 7;
        sumMagnitudes(7) = sumMagnitudes(7) + gradientMagnitude(i);

   elseif gradientAngle(i) > 67.5 & gradientAngle(i) <= 90 %bin 8
        gradientOrientation(i) = 8;
        sumMagnitudes(8) = sumMagnitudes(8) + gradientMagnitude(i);

    end
end

%4. Create the histogram
features = [1];

%5. Clip & Normalize the histogram

end
%end of file