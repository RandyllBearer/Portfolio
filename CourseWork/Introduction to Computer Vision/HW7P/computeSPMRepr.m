%Randyll Bearer: HW7P Part 1: Compute Spatial Pyramid Matching Representation

function [pyramid, level_0, level_1, level_2] = computeSPMRepr(im, sift, means);

%get size of the input image (for use in quadrant division)
[sizeX, sizeY, sizeZ] = size(im);

%Create bag-of-words representation of the image features where k = 50
[level_0] = computeBOWRepr(sift, means); %This equals L-0

%Divide Level-0 into four separate quadrants/histograms
[Frames, Descriptors] = vl_sift(im); %get SIFT frames/keypoints + Descriptors
[numResponses, numFeatures] = size(Descriptors); %Get numFeatures detected

middleX = floor(sizeX/2);   %Determine bounds of quadrants
middleY = floor(sizeY/2);   %Determine bounds of quadrants

q1YBoundMin = 0;        %Top Left Quadrant
q1YBoundMax = middleX;
q1XBoundMin = 0;
q1XBoundMax = middleY;

q2YBoundMin = middleX;%Top Right Quadrant
q2YBoundMax = sizeX;
q2XBoundMin = 0;
q2XBoundMax = middleY;

q3YBoundMin = 0;        %Bottom Left Quadrant
q3YBoundMax = middleX;
q3XBoundMin = middleY;
q3XBoundMax = sizeY;

q4YBoundMin = middleX;%Bottom Right Quadrant
q4YBoundMax = sizeX;
q4XBoundMin = middleY;
q4XBoundMax = sizeY;

q1Descriptors = {}; %To Store the descriptors for each quadrant
q2Descriptors = {};
q3Descriptors = {};
q4Descriptors = {};

for i=1:numFeatures %Loop through all detected features and assign them a quadrant
    featureX = Frames(1,i); %Get (x,y) coordinates of the detected feature
    featureY = Frames(2,i);
    
    if (featureX > q1XBoundMin) && (featureX < q1XBoundMax) && (featureY > q1YBoundMin) && (featureY < q1YBoundMax)
        q1Descriptors = [q1Descriptors, Descriptors(1:128,i)]; %Append to quadrant descriptor arrays
        
    elseif (featureX > q2XBoundMin) && (featureX < q2XBoundMax) && (featureY > q2YBoundMin) && (featureY < q2YBoundMax)
        q2Descriptors = [q2Descriptors, Descriptors(1:128,i)];
    
    elseif (featureX > q3XBoundMin) && (featureX < q3XBoundMax) && (featureY > q3YBoundMin) && (featureY < q3YBoundMax)
        q3Descriptors = [q3Descriptors, Descriptors(1:128,i)];
    
    elseif (featureX > q4XBoundMin) && (featureX < q4XBoundMax) && (featureY > q4YBoundMin) && (featureY < q4YBoundMax)
        q4Descriptors = [q4Descriptors, Descriptors(1:128,i)];
    
    end
    
end

[q1BOWResult] = computeBOWRepr(q1Descriptors, means); %This equals L-0
[q2BOWResult] = computeBOWRepr(q2Descriptors, means);
[q3BOWResult] = computeBOWRepr(q3Descriptors, means);
[q4BOWResult] = computeBOWRepr(q4Descriptors, means);

%Concatenate the four seperate histograms into 1 level-1 histogram
level_1 = [q1BOWResult, q2BOWResult, q3BOWResult, q4BOWResult];

%Concatenate level_2
level_2 = [0];

%Concatenate the level-0 and level-1 representations in pyramid output
pyramid = [level_0, level_1, level_2];

end
%End of File