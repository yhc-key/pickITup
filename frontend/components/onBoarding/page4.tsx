import Image from "next/image";

export default function Page4() {
  return (
    <div className="w-[100%] h-[100%]">
      <div className="flex flex-wrap justify-start align-middle ml-52">
        <div className="flex flex-col justify-evenly">
          <div className="flex flex-col justify-start text-4xl font-semibold tracking-widest">
            <div className="flex">
              <div className="my-1 ml-3 text-transparent bg-clip-text bg-gradient-to-r from-f5yellowgreen-200 to-f5green-300">
                간편한 이력 관리
              </div>
              <div className="my-1 text-f5black-400">를 위하여</div>
            </div>
            <div className="my-1 ml-3 text-f5black-400">
              유사 문항을 함께 관리할 수 있는 기능을 제공합니다
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
