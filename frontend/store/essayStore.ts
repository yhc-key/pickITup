import { create } from "zustand";

interface Title {
  id: number;
  title: string;
}

// Zustand 스토어 생성
interface EssayState {
  essayTitles: Title[];
  updateEssayTitles: (titles: Title[]) => void;
}

const useEssayStore = create<EssayState>((set) => ({
  essayTitles: [],
  updateEssayTitles: (titles: Title[]) => set({ essayTitles: titles }),
}));

export default useEssayStore;
