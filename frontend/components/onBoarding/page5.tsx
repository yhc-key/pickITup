import Image from "next/image";

export default function Page5() {
  return (
    <div className="w-[100%] h-[100%]">
      <div className="flex flex-wrap justify-start align-middle ml-52">
        <div className="flex flex-col justify-evenly">
          <div className="flex flex-col justify-start text-4xl font-semibold tracking-widest">
            <div className="my-1 ml-3 text-f5black-400">
              스피드 퀴즈, OX게임을 즐기며
            </div>
            <div className="flex">
              <div className="my-1 ml-3 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300">
                기술 면접 대비
              </div>
              <div className="my-1 text-f5black-400">를 해보세요!</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
