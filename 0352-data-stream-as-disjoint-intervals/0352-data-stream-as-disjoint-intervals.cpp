class SummaryRanges {
    map<int, int> intervals;
public:
    SummaryRanges() {
        
    }
    
    void addNum(int value) {
        auto right = intervals.lower_bound(value);
        auto left = right;

        if (left != intervals.begin())
            --left;
        else
            left = intervals.end();

        if (right != intervals.end() && right->first == value)
            return;

        if (left != intervals.end() && left->second >= value)
            return;
        
        bool mergeLeft = (left != intervals.end() && left->second +1 == value);
        bool mergeRight = (right != intervals.end() && right->first == value +1);

        if (mergeLeft && mergeRight) {
            left->second = right->second;
            intervals.erase(right);
        }
        else if (mergeLeft) {
            left->second = value;
        }
        else if (mergeRight) {
            int end = right->second;
            intervals.erase(right);
            intervals[value] = end;
        }
        else {
            intervals[value] = value;
        }
    }
    
    vector<vector<int>> getIntervals() {
        vector<vector<int>> res;
        for (auto &[l, r] : intervals) 
            res.push_back({l, r});
        return res;
    }
};

/**
 * Your SummaryRanges object will be instantiated and called as such:
 * SummaryRanges* obj = new SummaryRanges();
 * obj->addNum(value);
 * vector<vector<int>> param_2 = obj->getIntervals();
 */