class RandomizedCollection {
private:
    vector<int> nums;
    unordered_map<int, unordered_set<int>> mp;

public:
    RandomizedCollection() {
        
    }
    
    bool insert(int val) {
        bool notPresent = mp[val].empty();

        nums.push_back(val);
        mp[val].insert(nums.size() - 1);

        return notPresent;
    }
    
    bool remove(int val) {
        if (mp[val].empty())
            return false;

        int idx = *mp[val].begin();
        mp[val].erase(idx);

        int lastVal = nums.back();
        int lastIdx = nums.size() - 1;

        if (idx != lastIdx) {
            nums[idx] = lastVal;

            mp[lastVal].erase(lastIdx);
            mp[lastVal].insert(idx);
        }

        nums.pop_back();
        return true;
    }
    
    int getRandom() {
        return nums[rand() % nums.size()];
    }
};

/**
 * Your RandomizedCollection object will be instantiated and called as such:
 * RandomizedCollection* obj = new RandomizedCollection();
 * bool param_1 = obj->insert(val);
 * bool param_2 = obj->remove(val);
 * int param_3 = obj->getRandom();
 */