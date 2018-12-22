% load_split_dataset: load dataset, split into train/test

disp('Set the path to the scene dataset.');

scenes_dir = 'D:\School\Sem 8\Comp. Vision\HW7P\scenes_lazebnik\scenes_lazebnik';

all_scenes = dir(scenes_dir);
all_scenes_pruned = {};

all_images = {};
all_sift = {};
all_labels = [];
all_train_ids = [];
all_test_ids = [];

for i = (1 : length(all_scenes))    
    scene_entry = all_scenes(i);       
    
    % if the folder isn't '..' or '.'
    if (scene_entry.isdir && ~strcmp(scene_entry.name, '.') && ~strcmp(scene_entry.name, '..'))
        all_scenes_pruned = [all_scenes_pruned; scene_entry];
    end
end

assert(length(all_scenes_pruned) == 10); % we'll only use 10 categories

% for every scene folder...
% we'll use i as the scene category label
for i = (1 : length(all_scenes_pruned))
    
    scene_entry = all_scenes_pruned{i};      
    scene_dir = [scenes_dir '/' scene_entry.name];
    data_for_this_scene = dir(scene_dir);
    this_cat_sift = {};
    this_cat_images = {};
    
    % for every file within the given scene folder
    for j = (1 : length(data_for_this_scene))
        
        fprintf('Parsing scene type %u, image %u...\n', i, j); 

        % read and save image
        this_file = data_for_this_scene(j);
        % make sure it's a jpg file
        if ( (length(this_file.name) > 4) && strcmp(this_file.name(length(this_file.name)-3:length(this_file.name)), '.jpg') )
            im_path = [scene_dir '/' this_file.name];
            im = imread(im_path);
            this_cat_images = [this_cat_images; im];
            sift_path = [im_path '.mat'];
            sift = load(sift_path);
            this_cat_sift = [this_cat_sift; sift];
        end
    end
    
    % decide how to split the images for this category into train/test and
    % save this info
    r = randperm(length(this_cat_images));
    train_ids = length(all_labels) + r(1:30); % store indeces into the full set, not just indeces over the images in this category
    test_ids = length(all_labels) + r(31:60);
    all_train_ids = [all_train_ids; train_ids'];
    all_test_ids = [all_test_ids; test_ids'];
    
    % save images and labels
    all_labels = [all_labels; repmat(i, size(this_cat_images, 1), 1)];
    all_sift = [all_sift; this_cat_sift];
    all_images = [all_images; this_cat_images]; 
            
end

train_images = all_images(all_train_ids);
train_sift = all_sift(all_train_ids);
train_labels = all_labels(all_train_ids);
test_images = all_images(all_test_ids);
test_sift = all_sift(all_test_ids);
test_labels = all_labels(all_test_ids);

% grab SIFT features on the training set and run K-means

all_desc = [];

for i = 1:size(train_sift, 1)
    desc = train_sift{i}.d;
    all_desc = [all_desc desc];
end

K = 50;
[~, means] = kmeansML(K, all_desc);


