const Dot = ({ num, scrollIdx, handleDotClick }: { num: number; scrollIdx: number; handleDotClick: (num: number) => void }) => {
  return (
    <div
      className={`transition-all duration-500 ease-in-out relative w-3 border rounded-full cursor-pointer ${scrollIdx === num ? "h-8 bg-f5green-300" : "h-3 bg-f5gray-400"}`}
      onClick={() => handleDotClick(num)} // 클릭 이벤트 추가
    ></div>
  );
};

export default function Dots({ scrollIdx, handleDotClick }: { scrollIdx: number; handleDotClick: (num: number) => void }) {
  return (
    <div className="fixed top-[35%] right-12">
      <div className="flex flex-col justify-around h-44">
        <Dot num={1} scrollIdx={scrollIdx} handleDotClick={handleDotClick}></Dot>
        <Dot num={2} scrollIdx={scrollIdx} handleDotClick={handleDotClick}></Dot>
        <Dot num={3} scrollIdx={scrollIdx} handleDotClick={handleDotClick}></Dot>
        <Dot num={4} scrollIdx={scrollIdx} handleDotClick={handleDotClick}></Dot>
        <Dot num={5} scrollIdx={scrollIdx} handleDotClick={handleDotClick}></Dot>
      </div>
    </div>
  );
}