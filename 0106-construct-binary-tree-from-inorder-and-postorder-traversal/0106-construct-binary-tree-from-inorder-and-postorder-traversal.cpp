/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     TreeNode *left;
 *     TreeNode *right;
 *     TreeNode() : val(0), left(nullptr), right(nullptr) {}
 *     TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
 *     TreeNode(int x, TreeNode *left, TreeNode *right) : val(x), left(left), right(right) {}
 * };
 */
class Solution {
public:
    unordered_map<int, int> mp;
    int idx;

    TreeNode* buildTree(vector<int>& inorder, vector<int>& postorder, int left, int right) {
        if (left > right)
        return nullptr;

        int val = postorder[idx--];
        TreeNode* root = new TreeNode(val);

        int mid = mp[val];

        root->right = buildTree(inorder, postorder, mid+1, right);
        root->left = buildTree(inorder, postorder, left, mid-1);

        return root;
    }

    TreeNode* buildTree(vector<int>& inorder, vector<int>& postorder) {
        int n = inorder.size();

        for (int i=0; i<n; i++)
        mp[inorder[i]] = i;

        idx = n-1;

        return buildTree(inorder, postorder, 0, n-1);
    }
};