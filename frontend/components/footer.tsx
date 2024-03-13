import { IoLogoGitlab } from "react-icons/io5";

export default function Footer() {
    return (
    <footer className="mt-auto flex justify-evenly items-center bg-white text-black text-xs h-16 border-t border-[#d9d9d9]">
      <div className="w-1/12"></div>
      <div>Copyright © 2024. SSAFY 특화 A_406 F5 세희고침</div>
      <div className="w-3/12"></div>
      <div>서비스 이용약관</div>
      <div>개인 정보 처리방침</div>
      <div><IoLogoGitlab className="inline-block" /> Gitlab</div>
      <div className="w-1/12"></div>
    </footer>
    )
}