%Randyll Bearer: HW7P: Train/Predict SVM Labels

function [predicted_labels_test] = findLabelsSVM(pyramids_train, labels_train, pyramids_test);

%x = features (size of MxD)
%y = labels (size of Mx1)
model = fitcecoc(X, Y);

%X_test (size of NxD) = descriptors for the scenes whose labels you predict
label = predict(mmodel, X_test);












end
%End of File