class StreamChecker {
    struct TrieNode {
        TrieNode* child[26];
        bool isWord;

        TrieNode() {
            isWord = false;
            for (int i = 0; i < 26; i++)
                child[i] = nullptr;
        }
    };

    TrieNode* root;
    string stream;
    int maxLen;

public:
    StreamChecker(vector<string>& words) {
        root = new TrieNode();
        maxLen = 0;

        for (string &word : words) {
            maxLen = max(maxLen, (int)word.size());
            TrieNode* node = root;

            for (int i = word.size() - 1; i >= 0; i--) {
                int idx = word[i] - 'a';
                if (!node->child[idx])
                    node->child[idx] = new TrieNode();
                node = node->child[idx];
            }
            node->isWord = true;
        }
    }

    bool query(char letter) {
        stream.push_back(letter);

        if (stream.size() > maxLen)
            stream.erase(stream.begin());

        TrieNode* node = root;

        for (int i = stream.size() - 1; i >= 0; i--) {
            int idx = stream[i] - 'a';

            if (!node->child[idx])
                return false;

            node = node->child[idx];

            if (node->isWord)
                return true;
        }

        return false;
    }
};