const Dot = ({ num, scrollIdx }: { num: number; scrollIdx: number }) => {
  return (
    <div
      className={`transition-all duration-500 ease-in-out relative w-3 border rounded-full cursor-pointer ${scrollIdx === num ? "h-8 bg-f5green-300" : "h-3 bg-f5gray-400"}`}
    ></div>
  );
};

export default function Dots({ scrollIdx }: { scrollIdx: number }) {
  return (
    <div className="fixed top-[35%] right-12 mb:bottom-5 mb:left-1/2 mb:right-auto mb:top-auto mb:-rotate-90">
      <div className="flex flex-col justify-around h-44">
        <Dot num={1} scrollIdx={scrollIdx}></Dot>
        <Dot num={2} scrollIdx={scrollIdx}></Dot>
        <Dot num={3} scrollIdx={scrollIdx}></Dot>
        <Dot num={4} scrollIdx={scrollIdx}></Dot>
        <Dot num={5} scrollIdx={scrollIdx}></Dot>
      </div>
    </div>
  );
}
