class AllOne {
    struct Node {
        int cnt;
        unordered_set<string> keys;
        Node *prev, *next;

        Node(int c) : cnt(c), prev(nullptr), next(nullptr) {}
    };

    Node *head, *tail;
    unordered_map<string, Node*> mp;

    void insertAfter(Node* cur, Node* node) {
        node->next = cur->next;
        node->prev = cur;
        cur->next->prev = node;
        cur->next = node;

    }

    void removeNode(Node* node) {
        node->prev->next = node->next;
        node->next->prev = node->prev;
        delete node;
    };

public:
    AllOne() {
       head = new Node(0);
       tail = new Node(0);
       head->next = tail;
       tail->prev = head; 
    }
    
    void inc(string key) {
        if (!mp.count(key)) {
            if (head->next == tail || head->next->cnt != 1) {
                insertAfter(head, new Node(1));
            }
            head->next->keys.insert(key);
            mp[key] = head->next;
        } else {
            Node* cur = mp[key];
            Node* nxt = cur->next;

            if (nxt == tail || nxt->cnt != cur->cnt+1) {
                nxt = new Node(cur->cnt+1);
                insertAfter(cur, nxt);
            }
            nxt->keys.insert(key);
            mp[key] = nxt;

            cur->keys.erase(key);
            if (cur->keys.empty())
                removeNode(cur);
        }
    }
    
    void dec(string key) {
       Node* cur = mp[key];

       if (cur->cnt==1) {
        mp.erase(key);
       } else {
        Node* prv = cur->prev;

        if (prv==head || prv->cnt != cur->cnt-1) {
            Node* node = new Node(cur->cnt -1);

            node->prev = prv;
            node->next = cur;
            prv->next= node;
            cur->prev = node;

            prv = node;
        }

        prv->keys.insert(key);
        mp[key] = prv;
       }
       cur->keys.erase(key);

       if (cur->keys.empty())
        removeNode(cur);
    }
    
    string getMaxKey() {
        if (tail ->prev == head)
            return "";

            return *tail->prev->keys.begin();
    }
    
    string getMinKey() {
        if (head->next==tail)
            return "";

        return *head->next->keys.begin();
    }
};

/**
 * Your AllOne object will be instantiated and called as such:
 * AllOne* obj = new AllOne();
 * obj->inc(key);
 * obj->dec(key);
 * string param_3 = obj->getMaxKey();
 * string param_4 = obj->getMinKey();
 */