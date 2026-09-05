class NumArray {
    vector<int> tree;
    vector<int> nums;
    int n;
    void build(int node, int start, int end) {
        if (start == end) {
            tree[node] = nums[start];
            return;
        }
        int mid = start + (end - start) / 2;
        build(2 * node, start, mid);
        build(2 * node + 1, mid + 1, end);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }
    void updateTree(int node, int start, int end, int index, int val) {
        if (start == end) {
            tree[node] = val;
            return;
        }
        int mid = start + (end - start) / 2;
        if (index <= mid)
            updateTree(2 * node, start, mid, index, val);
        else
            updateTree(2 * node + 1, mid + 1, end, index, val);
        tree[node] = tree[2 * node] + tree[2 * node + 1];
    }
    int query(int node, int start, int end, int left, int right) {
        if (right < start || end < left)
            return 0;
        if (left <= start && end <= right)
            return tree[node];
        int mid = start + (end - start) / 2;
        return query(2 * node, start, mid, left, right) +
               query(2 * node + 1, mid + 1, end, left, right);
    }
public:
    NumArray(vector<int>& nums) {
        this->nums = nums;
        n = nums.size();
        tree.resize(4 * n);
        build(1, 0, n - 1);
    }
    void update(int index, int val) {
        nums[index] = val;
        updateTree(1, 0, n - 1, index, val);
    }
    int sumRange(int left, int right) {
        return query(1, 0, n - 1, left, right);
    }
};