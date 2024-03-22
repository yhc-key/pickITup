import {create} from 'zustand';

// Zustand 스토어 생성
export interface AuthState {
  isLoggedIn: boolean;
  nickname:string;
  login: (nickname: string) => void;
  logout: () => void;
}

const useAuthStore = create<AuthState>(set => ({
  isLoggedIn: false, // 기본값: 로그인되지 않음
  nickname:'',
  login: (nickname:string) => set({ isLoggedIn: true, nickname}), // 로그인 액션
  logout: () => set({ isLoggedIn: false, nickname: '' }), // 로그아웃 액션
}));

export default useAuthStore;