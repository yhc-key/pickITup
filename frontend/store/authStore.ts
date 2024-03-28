import {create} from 'zustand';

// Zustand 스토어 생성
export interface AuthState {
  isLoggedIn: boolean;
  nickname:string;
  github:string;
  blog:string;
  email:string;
  address:string;
  login: (nickname: string) => void;
  logout: () => void;
  setLogged : (nickname : string) => void;
  setNickname : (newNickname : string) => void;
  setGithub : (newGithub : string) => void;
  setBlog : (newBlog : string) => void;
  setEmail : (newEmail : string) => void;
  setAddress: (newAddress:string) =>void;
}

const useAuthStore = create<AuthState>(set => ({
  isLoggedIn: false, // 기본값: 로그인되지 않음
  nickname:'',
  github:'',
  blog:'',
  email:'',
  address:'',
  login: (nickname:string) => set({ isLoggedIn: true, nickname}), // 로그인 액션
  logout: () => set({ isLoggedIn: false, nickname: '' }), // 로그아웃 액션
  setLogged: (nickname:string) => set({isLoggedIn: true, nickname}),
  setNickname: (newNickname:string) => set({nickname:newNickname}),
  setGithub: (newGithub:string) => set({github:newGithub}),
  setBlog: (newBlog:string) => set({blog:newBlog}),
  setEmail: (newEmail:string) => set({email:newEmail}),
  setAddress:(newAddress:string) => set({address:newAddress}),
}));

export default useAuthStore;