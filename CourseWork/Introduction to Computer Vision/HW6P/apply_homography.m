%Randyll Bearer HW6P

%Form of p1 [x, y]

function [p2] = apply_homography(p1, H);
%Required for translation from homogenous coordinates to match dimensions
p1(3) = 1;

%Convert into a usable form
tempPoint = p1';

%Calculate result of the homography
result = H*tempPoint;

%Extract the corresponding image2 points
newX = result(1)/result(3);
newY = result(2)/result(3);

%Return the corresponding points
p2 = [newX, newY];

end
%End of File