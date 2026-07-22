class Solution {
public:
    vector<int> maxActiveSectionsAfterTrade(string s, vector<vector<int>>& queries) {
        int n = s.size();
        int totalOnesFull = 0;
        for (char c : s) if (c=='1') totalOnesFull++;

        // Run-length encode s
        vector<int> runStart, runLen; vector<char> runChar;
        for (int i=0;i<n;){
            int j=i; while(j<n && s[j]==s[i]) j++;
            runStart.push_back(i); runLen.push_back(j-i); runChar.push_back(s[i]);
            i=j;
        }
        int R = runStart.size();

        auto findRun = [&](int p)->int{
            int lo=0,hi=R-1,res=0;
            while(lo<=hi){int mid=(lo+hi)/2; if(runStart[mid]<=p){res=mid;lo=mid+1;} else hi=mid-1;}
            return res;
        };

        // mergeVal[k] (only for '1' runs): leftZero(full)+rightZero(full)  [no subtraction]
        // zeroLenArr[k] (only for '0' runs): runLen[k]
        // oneLenArr[k] (only for '1' runs): runLen[k]
        vector<int> mergeVal(R, INT_MIN), zeroLenArr(R, INT_MIN), oneLenArr(R, INT_MAX);
        for(int k=0;k<R;k++){
            if(runChar[k]=='1'){
                int leftZero = (k-1>=0 && runChar[k-1]=='0') ? runLen[k-1] : 0;
                int rightZero = (k+1<R && runChar[k+1]=='0') ? runLen[k+1] : 0;
                mergeVal[k] = leftZero + rightZero;
                oneLenArr[k] = runLen[k];
            } else {
                zeroLenArr[k] = runLen[k];
            }
        }

        int LOG=1; while((1<<LOG) < max(R,2)) LOG++;
        vector<vector<int>> spMerge(LOG+1, vector<int>(R, INT_MIN));
        vector<vector<int>> spZero(LOG+1, vector<int>(R, INT_MIN));
        vector<vector<int>> spOne(LOG+1, vector<int>(R, INT_MAX));
        for(int k=0;k<R;k++){ spMerge[0][k]=mergeVal[k]; spZero[0][k]=zeroLenArr[k]; spOne[0][k]=oneLenArr[k]; }
        for(int j=1;j<=LOG;j++)
            for(int k=0;k+(1<<j)<=R;k++){
                spMerge[j][k]=max(spMerge[j-1][k], spMerge[j-1][k+(1<<(j-1))]);
                spZero[j][k]=max(spZero[j-1][k], spZero[j-1][k+(1<<(j-1))]);
                spOne[j][k]=min(spOne[j-1][k], spOne[j-1][k+(1<<(j-1))]);
            }
        vector<int> lg(R+1,0);
        for(int i=2;i<=R;i++) lg[i]=lg[i/2]+1;

        auto qMerge=[&](int lo,int hi)->long long{
            if(lo>hi) return LLONG_MIN;
            int j=lg[hi-lo+1]; return max(spMerge[j][lo], spMerge[j][hi-(1<<j)+1]);
        };
        auto qZero=[&](int lo,int hi)->long long{
            if(lo>hi) return LLONG_MIN;
            int j=lg[hi-lo+1]; return max(spZero[j][lo], spZero[j][hi-(1<<j)+1]);
        };
        auto qOne=[&](int lo,int hi)->long long{
            if(lo>hi) return LLONG_MAX;
            int j=lg[hi-lo+1]; return min(spOne[j][lo], spOne[j][hi-(1<<j)+1]);
        };

        vector<int> ans(queries.size());
        for(size_t qi=0; qi<queries.size(); qi++){
            int l=queries[qi][0], r=queries[qi][1];
            int rl=findRun(l), rr=findRun(r);
            long long bestGain = 0;

            if (rl != rr) {
                int leftClip = runStart[rl]+runLen[rl]-l;   // clipped length of run rl within [l,r]
                int rightClip = r-runStart[rr]+1;            // clipped length of run rr within [l,r]

                if (rl+1 <= rr-1) {
                    // safe interior (both neighbors full, no clipping needed): [rl+2, rr-2]
                    long long safe = qMerge(rl+2, rr-2);
                    if (safe != LLONG_MIN) bestGain = max(bestGain, safe);

                    // boundary interior runs: k = rl+1 and k = rr-1
                    for (int k : {rl+1, rr-1}) {
                        if (k < rl+1 || k > rr-1) continue;
                        if (runChar[k] != '1') continue;
                        int leftZero = (k-1==rl) ? (runChar[rl]=='0'? leftClip:0) 
                                        : ((k-1>=0 && runChar[k-1]=='0') ? runLen[k-1] : 0);
                        int rightZero = (k+1==rr) ? (runChar[rr]=='0'? rightClip:0)
                                        : ((k+1<R && runChar[k+1]=='0') ? runLen[k+1] : 0);
                        bestGain = max(bestGain, (long long)leftZero + rightZero);
                    }

                    // separate case: maxZeroRun(in [l,r]) - minOneRunLen(interior only)
                    long long maxZero = LLONG_MIN;
                    if (runChar[rl]=='0') maxZero = max(maxZero, (long long)leftClip);
                    if (runChar[rr]=='0') maxZero = max(maxZero, (long long)rightClip);
                    long long zmid = qZero(rl+1, rr-1);
                    if (zmid != LLONG_MIN) maxZero = max(maxZero, zmid);

                    long long minOne = qOne(rl+1, rr-1); // interior runs never clipped

                    if (maxZero != LLONG_MIN && minOne != LLONG_MAX)
                        bestGain = max(bestGain, maxZero - minOne);
                }
                // if rl+1 > rr-1 (m==2, no interior run) -> bestGain stays 0
            }
            // if rl==rr (m==1) -> bestGain stays 0

            ans[qi] = totalOnesFull + (int)bestGain;
        }
        return ans;
    }
};