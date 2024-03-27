import { create } from "zustand";

// Zustand 스토어 생성
export interface searchState {
  keywords: string[];
  query: string;
  setKeywords: (keywords: string[]) => void;
  setQuery: (searchSentence: string) => void;
}

const useSearchStore = create<searchState>((set) => ({
  keywords: [""], // 기본값 : 비어있음
  query: "",
  setKeywords: (keywords: string[]) => set({ keywords }), // 로그인 액션
  setQuery: (searchSentence: string) => set({ query: searchSentence }),
}));

export default useSearchStore;
