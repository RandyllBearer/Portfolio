%Randyll Bearer    HW10P      4/11/2018

%As suggested by Piazza
setenv('EDITOR','vim'); 

%Let Matlab know where to find Caffe
addpath('/u/caffe/matlab/');

%Load-in pre-trained CNN model
net = caffe.Net('/u/caffe/hw_cs1674/models/deploy.prototxt', '/u/caffe/hw_cs1674/models/alexnet.caffemodel', 'test');
display('----- END TEST CNN LOAD SPAM -----');

%Calculate image_mean so we can subtract it from all input images
image_mean = caffe.io.read_mean('/u/caffe/hw_cs1674/models/mean227.binaryproto');

%Read in the imageSet (Of the Following Format)
%[1,2,3,...10] = folders
%read(imgSetVector(1),1) will return the first image from the first folder
%CALsuburb, MITcoast, MITforest, MIThighway, MITinsidecity, MITmountain,
%MITopencountry, MITstreet, MITtallbuilding, PARoffice
%60 images in each folder = 600 total images
imgSetVector = imageSet('/u/caffe/hw_cs1674/data/scenes_lazebnik/', 'recursive');

%Initialize a place to store fc6/fc7 & images for training
fc6 = zeros(10, 60, 4096);
fc7 = zeros(10, 60, 4096);

%Loop through each image in the dataset and extract fc6/fc7
for i=1:10
    string = '/u/caffe/hw_cs1674/data/scenes_lazebnik/';
    if (i==1)
        string = strcat(string, 'CALsuburb/');
    elseif (i==2)
        string = strcat(string, 'MITcoast/');
    elseif (i==3)
        string = strcat(string, 'MITforest/');
    elseif (i==4)
        string = strcat(string, 'MIThighway/');
    elseif (i==5)
        string = strcat(string, 'MITinsidecity/');
    elseif (i==6)
        string = strcat(string, 'MITmountain/');
    elseif (i==7)
        string = strcat(string, 'MITopencountry/');
    elseif (i==8)
        string = strcat(string, 'MITstreet/');
    elseif (i==9)
        string = strcat(string, 'MITtallbuilding/');
    elseif (i==10)
        string = strcat(string, 'PARoffice/');
    end
    
    for j=1:60
        newstring = string;
        if (j < 10)
            newstring = strcat(newstring, 'image_000');
        elseif (j <= 60)
            newstring = strcat(newstring, 'image_00');
        end
        
        newstring = strcat(newstring, int2str(j));
        newstring = strcat(newstring, '.jpg');
        
        %Load in the image
        loadedImage = caffe.io.load_image(newstring);
        
        %Convert to 'color'
        loadedImage = cat(3, loadedImage, loadedImage, loadedImage);
        
        %Resize
        loadedImage = imresize(loadedImage,[227 227]);
        
        %Subtract mean
        loadedImage = loadedImage - image_mean;
        
        %Run image through CNN
        net.forward({loadedImage});
        
        %Extract features from fc6 & fc7
        test = net.blobs('fc6').get_data();
        test = test';
        fc6(i, j, 1:4096) = test(1:4096);
        test = net.blobs('fc7').get_data();
        test = test';
        fc7(i, j, 1:4096) = test(1:4096);

    end

end

display('----- DONE EXTRACTING fc6 & fc7 -----');

%Compute labels
labels = zeros(1, 300); %to be input for fitcecoc()/predict()
labels(1:30) = 1;
labels(31:60) = 2;
labels(61:90) = 3;
labels(91:120) = 4;
labels(121:150) = 5;
labels(151:180) = 6;
labels(181:210) = 7;
labels(211:240) = 8;
labels(241:270) = 9;
labels(271:300) = 10;

%Convert fc6&fc7 into usable X inputs of type numeric arracy
fc6Resized = zeros(300, 4096); %to be input for fitcecoc()
fc7Resized = zeros(300, 4096);
fc6ResizedTesting = zeros(300, 4096); %to be input for predict()
fc7ResizedTesting = zeros(300, 4096);
indices = [1:60]; %to be randomized

currentPlace = 0;   %will go to 300
currentPlace2 = 0;
for i = 1:10
    %randomize the indices order for every class
    indices = randperm(numel(indices));
    
   for j = 1:60
       if (j<31)
        currentPlace = currentPlace+1;
        fc6Resized(currentPlace, 1:4096) = fc6(i,indices(j),1:4096);
        fc7Resized(currentPlace, 1:4096) = fc7(i,indices(j),1:4096);
        
       else
        currentPlace2 = currentPlace2+1;
        fc6ResizedTesting(currentPlace2, 1:4096) = fc6(i,indices(j),1:4096);
        fc7ResizedTesting(currentPlace2, 1:4096) = fc7(i,indices(j),1:4096);
           
       end
   end
end

%Train fitcecoc()
%x = sample data, y = labels
%length of Y and number of rows in X must be ==
fc6Model = fitcecoc(fc6Resized, labels);
fc7Model = fitcecoc(fc7Resized, labels);

display('----- Done Training fitcecoc() -----');

%Predict
fc6Label = predict(fc6Model, fc6ResizedTesting);
fc7Label = predict(fc7Model, fc7ResizedTesting);

display('----- Done Predicting -----');

display('----- fc6Label Results -----');
display(fc6Label);

display('----- fc7Label Results -----');
display(fc7Label);

display('----- Acurracy Results -----');
fc6Correct = 0;
fc7Correct = 0;
for i=1:300
    if i < 30
        if fc6Label(i) == 1
            fc6Correct = fc6Correct + 1;
        end
        
        if fc7Label(i) == 1
            fc7Correct = fc7Correct + 1;
        end
        
    elseif i < 60
        if fc6Label(i) == 2
            fc6Correct = fc6Correct + 1;
        end
        
        if fc7Label(i) == 2
            fc7Correct = fc7Correct + 1;
        end
        
    elseif i < 90
        if fc6Label(i) == 3
            fc6Correct = fc6Correct + 1;
        end
        
        if fc7Label(i) == 3
            fc7Correct = fc7Correct + 1;
        end
        
    elseif i < 120
        if fc6Label(i) == 4
            fc6Correct = fc6Correct + 1;
        end
        
        if fc7Label(i) == 4
            fc7Correct = fc7Correct + 1;
        end
        
    elseif i < 150
        if fc6Label(i) == 5
            fc6Correct = fc6Correct + 1;
        end
        
        if fc7Label(i) == 5
            fc7Correct = fc7Correct + 1;
        end
        
    elseif i < 180
        if fc6Label(i) == 6
            fc6Correct = fc6Correct + 1;
        end
        
        if fc7Label(i) == 6
            fc7Correct = fc7Correct + 1;
        end
        
    elseif i < 210
        if fc6Label(i) == 7
            fc6Correct = fc6Correct + 1;
        end
        
        if fc7Label(i) == 7
            fc7Correct = fc7Correct + 1;
        end
        
    elseif i < 240
        if fc6Label(i) == 8
            fc6Correct = fc6Correct + 1;
        end
        
        if fc7Label(i) == 8
            fc7Correct = fc7Correct + 1;
        end
        
    elseif i < 270
        if fc6Label(i) == 9
            fc6Correct = fc6Correct + 1;
        end
        
        if fc7Label(i) == 9
            fc7Correct = fc7Correct + 1;
        end
        
    elseif i < 300
        if fc6Label(i) == 10
            fc6Correct = fc6Correct + 1;
        end
        
        if fc7Label(i) == 10
            fc7Correct = fc7Correct + 1;
        end
        
    end
    
end

fc6Accuracy = (fc6Correct/300) * 100;
fc7Accuracy = (fc7Correct/300) * 100;
display('fc6 Accuracy: '); 
display(fc6Accuracy);
display('fc7 Accuracy: '); 
display(fc7Accuracy);

%End of File