interface ExperienceBarProps {
  prev: number;
  next: number;
  exp : number;
}
export default function ExperienceBar({prev,next,exp}:ExperienceBarProps){
  const percentage = (exp-prev)/(next-prev)*100;
  return(
    <div className="w-4/6 h-4 bg-gray-200 rounded-lg overflow-hidden shadow-lg">
      <div
        className="h-full bg-f5green-300 rounded-lg shadow-lg"
        style={{ width: `${percentage}%` }}
      ></div>
    </div>
  )
}