%Randyll Bearer: HW7P Part 2: Training/Obtaining labels from 2 classifiers
%k = value in K-Nearest-Neighbors
%K = value in K-Means
%pyramids_train: Mx1 size of training image set, pyramid = 1xd Spatial
%Pyramid Match representation of the correponding training or test image
%pyramids_test: Nx1 size of test image set,
%labels_train: Mx1 vector of training labels

function [predicted_labels_test] = findLabelsKNN(pyramids_train, labels_train, pyramids_test, k);

%Prepare output 
%predicted_labels_test = [predicted_labels_test, toAppend]; to append
predicted_labels_test = {};

%Get number of training/testing images
[numTestPyramids, ~] = size(pyramids_test);
[numTrainingPyramids, ~] = size(pyramids_train);

for i = 1:numTestPyramids
    %1,: = euclidean distance, 2,:  = trainingPyramid itself
    euclideanResults = zeros(2,numTrainingPyramids);
    for j = 1:numTrainingPyramids
        
        %Compute distance between test and training, put into results
        
    end
    
    kNearestPyramids = zeros(2, k);
    for k = 1:numTrainingPyramids
        
        %Narrow down to kNearest
        
    end
    
    modes = zeros(k);
    for l = 1:k
        
        %Find the mode of labels in these k-closest training images
        
    end
    
    for m = 1:k
        
        %find the mode of modes, assign this to predicted label
        predicted_labels_test = 0;
        
    end
    
end

end
%End of File